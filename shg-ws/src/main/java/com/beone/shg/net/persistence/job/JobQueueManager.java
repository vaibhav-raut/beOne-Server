package com.beone.shg.net.persistence.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.spo.GroupSPO;
import com.beone.shg.net.persistence.util.DataUtil;

public class JobQueueManager {

	private static Map<Long, JobQueue> jobQueueMap = new ConcurrentHashMap<Long, JobQueue>();

	public static void addToJobQueue(long groupAcNo, Runnable job) {
		addToJobQueue(groupAcNo, DataUtil.ZERO_INTEGER, job);
	}
	public static synchronized void addToJobQueue(long groupAcNo, int processSchedulerId, Runnable job) {

		// Get JobQueue If already generate for the Group
		JobQueue queue = jobQueueMap.get(groupAcNo);

		// Create JobQueue for the Group
		if(queue == null) {
			// Create Job Queue
			queue = new JobQueue(groupAcNo, processSchedulerId);
			
			// Register Job Queue
			jobQueueMap.put(groupAcNo, queue);
			
			// Add Job to Queue
			queue.addJob(job);
			
			// Start execution on Job Queue
			queue.execute();
			
		} else {
			// Add Job to Queue
			queue.addJob(job);
		}		
	}

	protected static void jobQueueDone(long groupAcNo) {
		jobQueueMap.remove(groupAcNo);
	}	

	public static int getGroupsInJobQueue() {
		return jobQueueMap.size();
	}	

	protected static class JobQueue implements Runnable {
		private final static Logger LOGGER = LoggerFactory.getLogger(GroupSPO.class);

		private long groupAcNo;
		private int processSchedulerId;
		private Map<Integer, Runnable> jobQueue = new ConcurrentHashMap<Integer, Runnable>();
		private Boolean addInProgress;
		private Integer noOfJobs;
		private Integer lockTiLLExecute;

		protected JobQueue(long groupAcNo, int processSchedulerId) {
			super();
			this.groupAcNo = groupAcNo;
			this.processSchedulerId = processSchedulerId;
			noOfJobs = 0;
			lockTiLLExecute = 0;
			addInProgress = false;
		}

		@Override
		public void run() {
			Integer jobRunIndex = 1;

			// Traverse through Job Queue and execute in serial
			while(jobQueue.get(jobRunIndex) != null) {
//				LOGGER.warn("Start Of Job: " + jobRunIndex);

				try {
					jobQueue.get(jobRunIndex).run();
				} catch (Exception e) {
					LOGGER.error("Job Index:" + jobRunIndex + "; " + e.toString());
				} finally {
					jobQueue.remove(jobRunIndex);
				}

//				LOGGER.warn("End Of Job: " + jobRunIndex);
				lockTiLLExecute--;

				// Manage Thread Hold / Wait logic
				holdThread(jobRunIndex);
				jobRunIndex++;
			}

			// Clear JobQueue and move from the Map
			jobQueue.clear();
			JobQueueManager.jobQueueDone(groupAcNo);
		}

		protected void holdThread(Integer jobRunIndex) {

			try {
				if(noOfJobs <= jobRunIndex) {
					Thread.sleep(500);
					while(addInProgress) {
						Thread.sleep(100);
					}
				}
			} catch (InterruptedException e) {
				LOGGER.error(e.toString());
			}
		}

		protected void addJob(Runnable job) {
			addInProgress = true;
			noOfJobs++;
			jobQueue.put(noOfJobs, job);
			addInProgress = false;

//			LOGGER.warn("Addition Of a Job: " + noOfJobs);
		}

		protected long getGroupAcNo() {
			return groupAcNo;
		}

		public int getProcessSchedulerId() {
			return processSchedulerId;
		}

		protected void execute() {
			PersistentThreadPool.getInstance().execute(this);
		}
	}
	
	protected static final class PersistentThreadPool {
		
		private static final int FIXED_NUMBER_OF_THREADS = 10;

		private static PersistentThreadPool instance;

		static {
			instance = new PersistentThreadPool();
		}

		public static PersistentThreadPool getInstance() {
			return instance;
		}

		private ExecutorService executor;

		private PersistentThreadPool() {
			executor = Executors.newFixedThreadPool(FIXED_NUMBER_OF_THREADS);
		}

		public void execute(Runnable job) {
			synchronized (executor) {
				executor.execute(job);
			}
		}
	}
}

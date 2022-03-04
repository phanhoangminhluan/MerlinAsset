package com.merlin.asset.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.30 12:26
 */
public class RetryUtils<R> {

    private static Logger logger = LoggerFactory.getLogger(RetryUtils.class);

    public static void retry(RetryVoidFunction retryVoidFunction, int attemps, long breakTime) {
        boolean isSuccess = false;
        int retryAttemp = 0;
        while (!isSuccess && retryAttemp < attemps) {
            try {
                retryAttemp++;
                retryVoidFunction.apply();
                isSuccess = true;
            } catch (Exception e) {
                logger.error(LogUtils.buildLogMsg(
                        "Attempt", retryAttemp,
                        "Status", "fail",
                        "Exception", LogUtils.getStackTrace(e)
                ));
            } finally {
                if (isSuccess) {
                    logger.info(LogUtils.buildLogMsg(
                            "Attempt", retryAttemp,
                            "Status", "success"
                    ));
                }
                if (!isSuccess) {
                    try {
                        Thread.sleep(breakTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (isSuccess) {
            logger.info("Retry success after " + retryAttemp + (retryAttemp == 1 ? " attempt" : " attempts"));
        } else {
            logger.info("Retry fail after " + attemps + " attempts");
            logger.error("Retry fail after " + attemps + " attempts");
        }
    }

    public static boolean retry(RetryBooleanFunction retryBooleanFunction, int attempts, long breakTime) {
        boolean isSuccess = false;
        int retryAttemp = 0;
        boolean result = false;
        while (!isSuccess && retryAttemp < attempts) {
            try {
                retryAttemp++;
                result = retryBooleanFunction.apply();
                isSuccess = true;
            } catch (Exception e) {
                logger.error(LogUtils.buildLogMsg(
                        "Attempt", retryAttemp,
                        "Status", "fail",
                        "Exception", LogUtils.getStackTrace(e)
                ));
            } finally {
                if (isSuccess) {
//                    logger.info(LogUtil.buildLogMsg(
//                            "Attempt", retryAttemp,
//                            "Status", "success"
//                    ));
                }
                if (!isSuccess) {
                    try {
                        Thread.sleep(breakTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (isSuccess) {
//            logger.info("Retry success after " + retryAttemp + (retryAttemp == 1 ? " attempt" : " attempts"));
        } else {
            logger.info("Retry fail after " + attempts + " attempts");
            logger.error("Retry fail after " + attempts + " attempts");
        }
        return result;
    }

    public interface RetryFunction {
    }

    public interface RetryVoidFunction extends RetryFunction {
        void apply() throws Exception;
    }

    public interface RetryBooleanFunction extends RetryFunction {
        boolean apply() throws Exception;
    }


}





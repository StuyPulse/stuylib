package com.stuypulse.stuylib.file;

import java.util.logging.SimpleFormatter;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that will let you log any class that implements loggable.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 * @author Ivan (ivanw8288@gmail.com)
 * @author Ayan ()
 */

public class FRCLogger {
    /**
     * The Loggable interface should be implemented by a class that can 
     * return a useful piece of data that can be called on demand to be written
     * into the log.
     */
    public interface Loggable {
        /**
         * Implement this method with something that reports
         * if any data from the class should be fed to the logger
         * this iteration.
         * 
         * @return if any data from the class will be fed to the logger.
         */
        public boolean logThisIteration();

        /**
         * Implement this method with something that
         * reports the level of severity the data should be
         * logged at.
         * 
         * @return the level of the data that will be fed.
         */
        public default Level getLogLevel() {
            return Level.INFO;
        }

        /**
         * Implement this method with something that 
         * reports the message or data that should be fed
         * into the logger as a string.
         * 
         * @return the data to be fed into the logger.
         */
        public String getLogData();
        
    }

    private Logger mLogger;
    private FileHandler mFileHandler;

    private ArrayList<Loggable> loggables;

    /**
     * Open a new FRCLogger
     * 
     * @param file Name of the file to be written to.
     */
    FRCLogger(String file) {
        mLogger = Logger.getLogger(FRCLogger.class.getName());

        try {
            mFileHandler = new FileHandler("./Logs/" + file + ".log");
        } catch (Exception e) {
            logError(this, e);
            e.printStackTrace();
        }

        mFileHandler.setFormatter(new SimpleFormatter());
        mLogger.addHandler(mFileHandler);

        loggables = new ArrayList<Loggable>();
    }

    /**
     * Registers a new Loggable that upon call of  will put the next
     * getLogData() at level getLogLevel() to the log if it will
     * logThisIteration().
     * 
     * @param loggable Class implementing Loggable
     */
    public void registerLoggable(Loggable loggable) {
        loggables.add(loggable);
    }

    /**
     * Iterates through all the registered loggables and logs them with level and data,
     * if logThisIteration() is true.
     */
    public void logRegisteredLoggables() {

        for (Loggable loggable: loggables) {
            if (loggable.logThisIteration()) {
                logManualLoggable(loggable);
            }
        }

    }

    /**
     * Forces Logger to log this Loggable.
     * 
     * @param loggable Loggable object to log.
     */
    public void logManualLoggable(Loggable loggable) {
        mLogger.log(loggable.getLogLevel(), loggable.getClass().getName() + ":\n" + loggable.getLogData());
    }

    /**
     * Adds a new log entry to the log.
     * 
     * @param level Level of severity of the log entry.
     * @param T Object that is the source of the log entry.
     * @param toLog Log entry message.
     */
    public <T> void logMisc(Level level, T obj, String toLog) {
        mLogger.log(level, obj.getClass().getName().toUpperCase() + ": ", toLog);
    }

    /**
     * Log an error.  Should be used in conjunction with try/catch.
     * 
     * @param obj Object that is the source of the error.
     * @param e Exception, usually from try/catch
     */
    public <T> void logError(T obj, Exception e) {
        mLogger.severe("Exception thrown from " + obj.getClass().getName().toUpperCase() + ":\n" + e);
    }

}
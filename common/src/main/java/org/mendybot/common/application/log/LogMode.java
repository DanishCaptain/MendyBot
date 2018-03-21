package org.mendybot.common.application.log;

/**
 * 
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 05/20/10 Brian Sorensen Inspection clean-up
 * </pre>
 */
public enum LogMode
{
  /** Logs to standard out*/
  NORMAL, 
  /** Logs to file */
  FILE,
  /** Logs to file and screen */
  BOTH
}

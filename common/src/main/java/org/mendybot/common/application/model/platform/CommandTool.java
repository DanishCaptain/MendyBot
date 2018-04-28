package org.mendybot.common.application.model.platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mendybot.common.exception.ExecuteException;

public class CommandTool
{
  private final ArrayList<String> output = new ArrayList<>();
  private final ArrayList<String> errors = new ArrayList<>();
  private String command;
  
  public CommandTool(String command)
  {
    this.command = command;
  }
  
  public List<String> getOutput()
  {
    return output;
  }
  
  public List<String> getErrors()
  {
    return errors;
  }
  
  public int call() throws ExecuteException
  {
    // Running the above command
    Runtime run = Runtime.getRuntime();
    try
    {
      Process proc = run.exec(command);
      new Watcher(output, proc.getInputStream());
      new Watcher(errors, proc.getErrorStream());
      proc.waitFor();
      proc.destroy();
      return proc.exitValue();
    }
    catch (IOException e)
    {
      throw new ExecuteException(e);
    }
    catch (InterruptedException e)
    {
      return -10;
    }
  }

}

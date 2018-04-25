package org.mendybot.common.role;

import org.mendybot.common.exception.ExecuteException;

public interface MendyBotRole
{

  void init() throws ExecuteException;

  void start() throws ExecuteException;

  void stop();
}

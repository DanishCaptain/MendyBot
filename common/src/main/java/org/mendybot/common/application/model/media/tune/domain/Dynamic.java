package org.mendybot.common.application.model.media.tune.domain;

public enum Dynamic
{
  PPPP(8), PPP(20), PP(31), P(42), MP(53), MF(64), F(80), FF(96), FFF(112), FFFF(127);

  private int value;

  Dynamic(int value) {
    this.value=value;
  }
  
  public int getValue()
  {
    return value;
  }
}

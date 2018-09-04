package org.mendybot.common.application.model.widget.weather;

import java.util.Arrays;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.model.domain.DomainAttribute;
import org.mendybot.common.model.domain.DomainModel;
import org.mendybot.common.model.domain.attribute.TimeAttribute;
import org.mendybot.common.model.domain.attribute.TimestampAttribute;
import org.mendybot.common.model.domain.general.InfoAttribute;
import org.mendybot.common.model.domain.location.CityAttribute;
import org.mendybot.common.model.domain.location.CountryAttribute;
import org.mendybot.common.model.domain.location.GeographicCoordinateLatitude;
import org.mendybot.common.model.domain.location.GeographicCoordinateLongitude;
import org.mendybot.common.model.domain.measures.HeadingAttribute;
import org.mendybot.common.model.domain.measures.HumidityAttribute;
import org.mendybot.common.model.domain.measures.VelocityAttribute;
import org.mendybot.common.model.domain.measures.pressure.PressureAttribute;
import org.mendybot.common.model.domain.measures.temperature.TemperatureAttribute;

public class WeatherWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(WeatherWidget.class);
  public static final String MODEL_NAME = "WEATHER_INFO";
  public static final String DD_LOCATION = "WeatherLocation";
  public static final String DD_CURRENT_WEATHER = "WeatherCurrentInfo";
  public static final String LOCATION_CITY = "WeatherLocationCity";
  public static final String LOCATION_COUNTRY = "WeatherLocationCountry";
  public static final String LOCATION_LATITUDE = "WeatherLocationLatitude";
  public static final String LOCATION_LONGITUDE = "WeatherLocationLongitude";
  public static final String WEATHER_REPORT_CREATED = "WeatherReportCreated";
  public static final String WEATHER_REPORT_LOCATION = "WeatherReportLocation";
  public static final String WEATHER_REPORT_DESCRIPTION = "WeatherReportDescription";
  public static final String WEATHER_REPORT_SUNRISE = "WeatherReportSunrise";
  public static final String WEATHER_REPORT_SUNSET = "WeatherReportSunset";
  public static final String WEATHER_REPORT_TEMPERATURE = "WeatherReportTemperature";
  public static final String WEATHER_REPORT_TEMPERATURE_LOW = "WeatherReportTemperatureLow";
  public static final String WEATHER_REPORT_TEMPERATURE_HIGH = "WeatherReportTemperatureHigh";
  public static final String WEATHER_REPORT_HUMIDITY = "WeatherReportHumidity";
  public static final String WEATHER_REPORT_ATMOSPHERIC_PRESSURE = "WeatherReportAtmosphericPressure";
  public static final String WEATHER_REPORT_WIND_DIRECTION = "WeatherReportWindDirection";
  public static final String WEATHER_REPORT_WIND_SPEED = "WeatherReportWindSpeed";
  public static final String WEATHER_REPORT_LAST_UPDATE = "WeatherReportLastUpdate";
  private WeatherQueue ticker = new WeatherQueue();
  private DomainModel domainModel;

  public WeatherWidget(ApplicationModel model, String name)
  {
    super(model, name);
    domainModel = DomainModel.lookup(WeatherWidget.MODEL_NAME);
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logDebug("init", "call");
    /*
  private String iconImage;
     */
    domainModel.createDefinition(DD_CURRENT_WEATHER, Arrays.asList(new DomainAttribute[]{
        new TimestampAttribute(WEATHER_REPORT_CREATED),
        new InfoAttribute(WEATHER_REPORT_DESCRIPTION),
        new TimeAttribute(WEATHER_REPORT_SUNRISE),
        new TimeAttribute(WEATHER_REPORT_SUNSET),
        new TemperatureAttribute(WEATHER_REPORT_TEMPERATURE),
        new TemperatureAttribute(WEATHER_REPORT_TEMPERATURE_LOW),
        new TemperatureAttribute(WEATHER_REPORT_TEMPERATURE_HIGH),
        new HumidityAttribute(WEATHER_REPORT_HUMIDITY),
        new PressureAttribute(WEATHER_REPORT_ATMOSPHERIC_PRESSURE),
        new HeadingAttribute(WEATHER_REPORT_WIND_DIRECTION),
        new VelocityAttribute(WEATHER_REPORT_WIND_SPEED),
        new TimeAttribute(WEATHER_REPORT_LAST_UPDATE),
    }));
    domainModel.createDefinition(DD_LOCATION, Arrays.asList(new DomainAttribute[]{
        new CityAttribute(LOCATION_CITY),
        new CountryAttribute(LOCATION_COUNTRY),
        new GeographicCoordinateLatitude(LOCATION_LATITUDE),
        new GeographicCoordinateLongitude(LOCATION_LONGITUDE),        
    }));
  }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logDebug("start", "call");
    ticker.start();
  }

  @Override
  public void stop()
  {
    LOG.logDebug("stop", "call");
    ticker.stop();
  }

  public void addWeatherListener(WeatherListener lis)
  {
    ticker.addWeatherListener(lis);
  }

  public void removeWeatherListener(WeatherListener lis)
  {
    ticker.removeWeatherListener(lis);
  }

}

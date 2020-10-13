#!/usr/bin/python3
from w1thermsensor import W1ThermSensor

sensor = W1ThermSensor()
temp_in_celsius = sensor.get_temperature()
print(temp_in_celsius)

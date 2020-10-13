#! /usr/bin/python3

import RPi.GPIO as gpio
import signal
from time import sleep


def led_handler(pin: int):
    global led_on
    if pin == button_pin:
        if led_on:
            gpio.output(led_pin, gpio.LOW)
            led_on = False
        else:
            gpio.output(led_pin, gpio.HIGH)
            led_on = True


def signal_handler(signum, frame):
    print("Received signal {}.".format(signum))
    gpio.cleanup()
    exit(0)


if __name__ == "__main__":
    gpio.setmode(gpio.BOARD)

    button_pin = 3
    led_pin = 12
    gpio.setup(button_pin, gpio.IN, pull_up_down=gpio.PUD_UP)
    gpio.setup(led_pin, gpio.OUT)

    gpio.output(led_pin, gpio.LOW)
    led_on = False

    gpio.add_event_detect(button_pin, gpio.BOTH, bouncetime=200)
    gpio.add_event_callback(button_pin, led_handler)

    signal.signal(signal.SIGINT, signal_handler)

    while (True):
        sleep(60)

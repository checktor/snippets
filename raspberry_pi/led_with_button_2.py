#! /usr/bin/python3

import gpiozero
import signal
from time import sleep


def signal_handler(signum, frame):
    print("Received signal {}.".format(signum))
    exit(0)


def switch_led():
    global led_on
    if led_on:
        led.off()
        led_on = False
    else:
        led.on()
        led_on = True
    print(led_on)


if __name__ == "__main__":
    button_pin = 2
    led_pin = 18
    led = gpiozero.LED(led_pin)
    button = gpiozero.Button(button_pin, pull_up=True, bounce_time=200)

    led.off()
    led_on = False

    signal.signal(signal.SIGINT, signal_handler)

    while (True):
        if button.is_pressed:
            switch_led()
        sleep(0.2)

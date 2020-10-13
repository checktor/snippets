#! /usr/bin/python3

import Adafruit_CharLCD as LCD
import signal
import subprocess
import time

def signal_handler(signum, frame):
    print("Received signal {}.".format(signum))
    lcd.clear()
    exit(0)

if __name__ == "__main__":
    lcd_rs = 7
    lcd_en = 8
    lcd_d4 = 18
    lcd_d5 = 23
    lcd_d6 = 24
    lcd_d7 = 25

    lcd_columns = 16
    lcd_rows = 2

    lcd = LCD.Adafruit_CharLCD(lcd_rs, lcd_en, lcd_d4, lcd_d5, lcd_d6, lcd_d7, lcd_columns, lcd_rows)

    signal.signal(signal.SIGINT, signal_handler)

    while(True):
        tempProcess = subprocess.Popen("/opt/vc/bin/vcgencmd measure_temp", shell=True, stdout=subprocess.PIPE)
        currentTemp = float(tempProcess.stdout.read()[5:9])
        currentTime = time.strftime("%d. %b. %H:%M")
        lcd.message("CPU-Temp.: {}\n{}".format(currentTemp, currentTime))
        lcd.home()
        time.sleep(30)





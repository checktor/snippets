#include <csignal>
#include <stdlib.h>
#include <wiringPi.h>

#define LED_PIN (1)
#define BUTTON_PIN (8)

bool led_on;

void signalHandler(int signum) {
        pinMode(LED_PIN, OUTPUT);
        digitalWrite(LED_PIN, 0);
        std::exit(signum);
}

void switch_led() {
        if (led_on) {
                digitalWrite(LED_PIN, 0);
                led_on = FALSE;
        }
        else {
                digitalWrite(LED_PIN, 1);
                led_on = TRUE;
        }
}

int main() {
        if (wiringPiSetup() == -1) {
                return -1;
        }

        pinMode(BUTTON_PIN, INPUT);
        pinMode(LED_PIN, OUTPUT);

        digitalWrite(LED_PIN, 0);
        bool led_on = FALSE;

        signal(SIGINT, signalHandler);

        while (TRUE) {
                if (digitalRead(BUTTON_PIN) == 0) {
                        switch_led();
                }
                delay(200);
        }

        return 0;
}

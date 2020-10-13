<!doctype html>
<html lang="de">

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  </head>

  <body>
    <?php
    $led_pin = '1';
    exec("gpio mode $led_pin out");

    if (isset($_POST['new_state'])) {
      $new_state = $_POST['new_state'];
      if ($new_state == '0' || $new_state == '1') {
        exec("gpio write $led_pin $new_state");
      }
    }

    $result = exec("gpio read $led_pin");

    if ($result == '0') {
      echo "<p>LED ausgeschaltet.</p>";
      echo "<form action='index.php' method='post'>";
      echo "<input type='hidden' name='new_state' value='1'>";
      echo "<input type='submit' value='LED einschalten'>";
      echo "</form>";
    } else {
      echo "<p>LED eingeschaltet.</p>";
      echo "<form action='index.php' method='post'>";
      echo "<input type='hidden' name='new_state' value='0'>";
      echo "<input type='submit' value='LED ausschalten'>";
      echo "</form>";
    }
    ?>
  </body>
  
</html>

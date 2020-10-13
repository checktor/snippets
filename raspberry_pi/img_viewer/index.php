<!doctype html>
<html lang="de">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <title>Image Viewer</title>
</head>

<body>

  <header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container d-flex justify-content-between">
        <a href="#" class="navbar-brand d-flex align-items-center">
          <strong>Album</strong>
        </a>
      </div>
    </div>
  </header>

  <main role="main">
    <div class="container">
      <div class="album bg-light">
        <?php
        $base = 'static/img';
        $images = glob("{$base}/*");
        echo '<div class="row align-items-center my-3">';
        $img_per_row = 3;
	$counter = 0;
        foreach($images as $image) {
          $counter++;
          echo '<div class="col">';
          echo "<img src=\"{$image}\" class=\"rounded img-thumbnail\" />";
          echo '</div>';
          if (($counter % $img_per_row) == 0) {
            echo '</div>';
            echo '<div class="row align-items-center my-3">';
          }
        }
        ?>
      </div>
    </div>
  </main>

</body>

</html>

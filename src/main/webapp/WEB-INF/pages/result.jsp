
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>jQuery Iframe Transport Plugin Redirect Page</title>
  </head>
  <body>
    <script>
      document.body.innerText = document.body.textContent = decodeURIComponent(
        window.location.search.slice(1)
      );
    </script>
  </body>
</html>
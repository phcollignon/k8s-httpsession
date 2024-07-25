<!DOCTYPE html>
<html>
<head>
    <title>Counter</title>
</head>
<body>
    <h1>Counter: ${counter.count}</h1>
    <form action="/increment" method="post">
        <button type="submit">Increment</button>
    </form>
    <form action="/reset" method="post">
        <button type="submit">Reset</button>
    </form>
</body>
</html>

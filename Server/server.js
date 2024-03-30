const express = require('express');

const app = express();
 
const port = 3000;

var logger = require('morgan');

const database = require('./db');
const apiMobile = require('./api');

app.use(express.json());
app.use(logger('dev'))

app.use('/api',apiMobile);
database.connect();


app.listen(port, () => {
  console.log("Example app listening on port " + port);
});

app.get('/',(rq,rs)=>{
    rs.send('WEB')
})
module.exports = app;
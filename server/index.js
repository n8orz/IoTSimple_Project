//initialize firebase
var admin = require('firebase-admin');
admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  databaseURL: '<your-db>'
});
//initialize express
const express = require('express')
const app = express()
const port = 3000
//Structure incoming data
app.get('/users/:email/sensor/:sensorName/data/:dataVal/', function (req, res) {
  var parse = req.params
  console.log(parse.email)
  res.send(writeUserData(parse.email,parse.sensorName,parse.dataVal))
  console.log(req.params)
})

app.listen(port, () => console.log(`Example app listening at http://localhost:${port}`))
//test function for writing data
function writeUserData(email, sensorName, dataVal) {
  const currTime = new Date(Date.now())
  const jsonTime = currTime.toJSON().toString()
  console.log(jsonTime)
  admin.database().ref('users/' + email + '/dataList/').push({
    sensor: sensorName,
    // admin.database.ServerValue.TIMESTAMP
    time: admin.database.ServerValue.TIMESTAMP,
    data_point: dataVal
  });
}

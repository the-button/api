const database = require('./database/index');
const loglevel = require('loglevel');
const sockets = require('./sockets');
require('./sentry');

loglevel.setLevel(loglevel.levels.DEBUG);

let port = process.env.PORT || 8080;

let databaseConfig;
if (process.env.CLEARDB_DATABASE_URL) {
  databaseConfig = process.env.CLEARDB_DATABASE_URL;
} else {
  databaseConfig = require('../config/database');
}

database(databaseConfig).then(connection => {
  sockets(connection, port);
});

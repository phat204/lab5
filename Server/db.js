const mongoose = require('mongoose');

const COMON = require('./COMON');

mongoose.set('strictQuery', true);

const connect = async () => {
    try {
        await mongoose.connect(COMON.uri);
        console.log("Connect successfully");
    } catch (error) {
        console.log(error);
    }
};

module.exports = {connect};
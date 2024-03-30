const mongoose = require('mongoose');

const Scheme = mongoose.Schema;

const Distributors = new Scheme({
    name: {
        type: String,
        unique: true,
        maxlenght: 255
    },
},{
    timestamps: true
});
module.exports = mongoose.model('distributor', Distributors);

var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'VolumeControl', 'coolMethod', [arg0]);
};

//设置音量
exports.setVolume = function (arg0, success, error) {
  exec(success, error, 'VolumeControl', 'setVolume', [arg0]);
};

exports.getVolume = function (success, error) {
  exec(success, error, 'VolumeControl', 'getVolume', []);
};

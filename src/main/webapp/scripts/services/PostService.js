angular.module('jobzz').service('postService', function () {
    var post = {};

    var setPost = function (newObj) {
        post = newObj;
    };

    var getPost = function () {
        return post;
    };

    return {
        setPost: setPost,
        getPost: getPost
    };

});

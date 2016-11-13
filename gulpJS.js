var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');

var config = {
    publicDir: 'src/main/webapp',
    dirJQuery: 'src/main/webapp/components/jquery/dist/jquery.min.js',
    dirBootstrapJS: 'src/main/webapp/components/bootstrap-sass/assets/javascripts/bootstrap.min.js',
    dirAngularJS: 'src/main/webapp/components/angular/angular.min.js',
    dirAngularRootJS: 'src/main/webapp/components/angular-route/angular-route.min.js'
};

gulp.task('scripts', function () {
    gulp.src([config.dirJQuery, config.dirBootstrapJS, config.dirAngularJS, config.dirAngularRootJS])
        .pipe(concat('all.js'))
        .pipe(uglify())
        .pipe(gulp.dest(config.publicDir + '/js'))
});

gulp.task('default', ['scripts']);
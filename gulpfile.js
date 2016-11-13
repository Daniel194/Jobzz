var gulp = require('gulp');
var sass = require('gulp-sass');

var config = {
    bootstrapDir: 'src/main/webapp/components/bootstrap-sass/assets/stylesheets/bootstrap',
    publicDir: 'src/main/webapp'
};

gulp.task('css', function () {
    return gulp.src('src/main/webapp/css/main.scss')
        .pipe(sass({
            includePaths: [config.bootstrapDir + '/assets/stylesheets']
        }))
        .pipe(gulp.dest(config.publicDir + '/css'));
});

gulp.task('fonts', function () {
    return gulp.src(config.bootstrapDir + '/assets/fonts/**/*')
        .pipe(gulp.dest(config.publicDir + '/fonts'));
});

gulp.task('default', ['css', 'fonts']);
var gulp = require('gulp');
var babel = require("gulp-babel");
var sass = require('gulp-sass');

gulp.task("default", function () {
	  return gulp.src("*.js")
	    .pipe(babel())
	    .pipe(gulp.dest("dist"));
});

gulp.task('sass', function () {
	  return gulp.src('./sass/**/*.scss')
	    .pipe(sass().on('error', sass.logError))
	    .pipe(gulp.dest('./css'));
});
	 
	gulp.task('sass:watch', function () {
	  gulp.watch('./sass/**/*.scss', ['sass']);
});
var gulp = require('gulp');
var uglify = require('gulp-uglify');

gulp.task('default', function() {
	  return gulp.src("noinlinejs.js")
	  	.pipe(uglify())
	  	.pipe(gulp.dest("dist"));
});

gulp.task('compress', function() {
	  return gulp.src("noinlinejs.js")
	  	.pipe(uglify())
	  	.pipe(gulp.dest(""));
});
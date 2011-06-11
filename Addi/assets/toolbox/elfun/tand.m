## -*- texinfo -*-
## @deftypefn {Function File} {} tand (@var{x})
## Compute the tangent for each element of @var{x} in degrees.  Returns zero 
## for elements where @code{@var{x}/180} is an integer and @code{Inf} for
## elements where @code{(@var{x}-90)/180} is an integer.
## @seealso{atand, tan}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = tand (x)
  if (nargin != 1)
    print_usage ();
  endif
  I0 = x / 180;
  I90 = (x-90) / 180;
  y = tan (I0 .* pi);
  y(I0 == round (I0) & finite (I0)) = 0;
  y(I90 == round (I90) & finite (I90)) = Inf;
endfunction;

%!error(tand())
%!error(tand(1,2))
%!assert(tand(10:10:80),tan(pi*[10:10:80]/180),-10*eps)
%!assert(tand([0,180,360]) == 0)
%!assert(tand([90,270]) == Inf)

## -*- texinfo -*-
## @deftypefn {Function File} {} cosd (@var{x})
## Compute the cosine for each element of @var{x} in degrees.  Returns zero 
## for elements where @code{(@var{x}-90)/180} is an integer.
## @seealso{acosd, cos}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = cosd (x)
  if (nargin != 1)
    print_usage ();
  endif
  I = x / 180;
  y = cos (I .* pi);
  I = I + 0.5;
  y(I == round (I) & finite (I)) = 0;
endfunction

%!error(cosd())
%!error(cosd(1,2))
%!assert(cosd(0:10:80),cos(pi*[0:10:80]/180),-10*eps)
%!assert(cosd([0,180,360]) != 0)
%!assert(cosd([90,270]) == 0)

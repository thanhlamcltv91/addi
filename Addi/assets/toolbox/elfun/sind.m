## -*- texinfo -*-
## @deftypefn {Function File} {} sind (@var{x})
## Compute the sine for each element of @var{x} in degrees.  Returns zero 
## for elements where @code{@var{x}/180} is an integer.
## @seealso{asind, sin}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = sind (x)
  if (nargin != 1)
    print_usage ();
  endif
  I = x / 180;
  y = sin (I .* pi);
  y(I == round (I) & finite (I)) = 0;
endfunction

%!error(sind())
%!error(sind(1,2))
%!assert(sind(10:10:90),sin(pi*[10:10:90]/180),-10*eps)
%!assert(sind([0,180,360]) == 0)
%!assert(sind([90,270]) != 0)

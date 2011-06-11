## -*- texinfo -*-
## @deftypefn {Mapping Function} {} cot (@var{x})
## Compute the cotangent for each element of @var{x} in radians.
## @seealso{acot, cotd, coth}
## @end deftypefn

## Author: jwe

function w = cot (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ tan(z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! x = [pi/6, pi/4, pi/3, pi/2, 2*pi/3, 3*pi/4, 5*pi/6];
%! v = [rt3, 1, rt3/3, 0, -rt3/3, -1, -rt3];
%! assert(all (abs (cot (x) - v) < sqrt (eps)));

%!error cot ();

%!error cot (1, 2);


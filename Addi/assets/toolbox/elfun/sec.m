## -*- texinfo -*-
## @deftypefn {Mapping Function} {} sec (@var{x})
## Compute the secant for each element of @var{x} in radians.
## @seealso{asec, secd, sech}
## @end deftypefn

## Author: jwe

function w = sec (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ cos(z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! x = [0, pi/6, pi/4, pi/3, 2*pi/3, 3*pi/4, 5*pi/6, pi];
%! v = [1, 2*rt3/3, rt2, 2, -2, -rt2, -2*rt3/3, -1];
%! assert(all (abs (sec (x) - v) < sqrt (eps)));

%!error sec ();

%!error sec (1, 2);


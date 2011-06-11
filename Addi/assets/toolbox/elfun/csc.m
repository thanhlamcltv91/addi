## -*- texinfo -*-
## @deftypefn {Mapping Function} {} csc (@var{x})
## Compute the cosecant for each element of @var{x} in radians.
## @seealso{acsc, cscd, csch}
## @end deftypefn

## Author: jwe

function w = csc (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ sin(z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! x = [pi/6, pi/4, pi/3, pi/2, 2*pi/3, 3*pi/4, 5*pi/6];
%! v = [2, rt2, 2*rt3/3, 1, 2*rt3/3, rt2, 2];
%! assert(all (abs (csc (x) - v) < sqrt (eps)));

%!error csc ();

%!error csc (1, 2);


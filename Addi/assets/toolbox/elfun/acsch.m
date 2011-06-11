## -*- texinfo -*-
## @deftypefn {Mapping Function} {} acsch (@var{x})
## Compute the inverse hyperbolic cosecant of each element of @var{x}.
## @seealso{csch}
## @end deftypefn

## Author: jwe

function w = acsch (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = asinh (1 ./ z);

endfunction

%!test
%! v = [pi/2*i, -pi/2*i];
%! x = [-i, i];
%! assert(all (abs (acsch (x) - v) < sqrt (eps)));

%!error acsch ();

%!error acsch (1, 2);


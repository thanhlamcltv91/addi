## -*- texinfo -*-
## @deftypefn {Mapping Function} {} csch (@var{x})
## Compute the hyperbolic cosecant of each element of @var{x}.
## @seealso{acsch}
## @end deftypefn

## Author: jwe

function w = csch (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ sinh(z);

endfunction

%!test
%! x = [pi/2*i, 3*pi/2*i];
%! v = [-i, i];
%! assert(all (abs (csch (x) - v) < sqrt (eps)));

%!error csch ();

%!error csch (1, 2);


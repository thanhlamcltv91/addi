## -*- texinfo -*-
## @deftypefn {Mapping Function} {} sech (@var{x})
## Compute the hyperbolic secant of each element of @var{x}.
## @seealso{asech}
## @end deftypefn

## Author: jwe

function w = sech (z)

if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ cosh(z);

endfunction

%!test
%! x = [0, pi*i];
%! v = [1, -1];
%! assert(all (abs (sech (x) - v) < sqrt (eps)));

%!error sech ();

%!error sech (1, 2);


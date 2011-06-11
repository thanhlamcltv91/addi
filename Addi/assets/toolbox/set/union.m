## -*- texinfo -*-
## @deftypefn {Function File} {} union (@var{a}, @var{b})
## @deftypefnx{Function File} {} union (@var{a}, @var{b}, "rows")
## Return the set of elements that are in either of the sets @var{a} and
## @var{b}.  For example,
##
## @example
## @group
## union ([1, 2, 4], [2, 3, 5])
##      @result{} [1, 2, 3, 4, 5]
## @end group
## @end example
##
## If the optional third input argument is the string "rows" each row of
## the matrices @var{a} and @var{b} will be considered an element of sets.
## For example,
## @example
## @group
## union([1, 2; 2, 3], [1, 2; 3, 4], "rows")
##      @result{}  1   2
##     2   3
##     3   4
## @end group
## @end example
##
## @deftypefnx {Function File} {[@var{c}, @var{ia}, @var{ib}] =} union (@var{a}, @var{b})
##
## Return index vectors @var{ia} and @var{ib} such that @code{a == c(ia)} and
## @code{b == c(ib)}.
## 
## @seealso{intersect, complement, unique}
## @end deftypefn

## Author: jwe

function [y, ia, ib] = union (a, b, varargin)

  if (nargin < 2 || nargin > 3)
    print_usage ();
  endif

  if (nargin == 3 && ! strcmpi (varargin{1}, "rows"))
    error ("union: if a third input argument is present, it must be the string 'rows'");
  endif

  if (nargin == 2)
    y = [a(:); b(:)];
    na = numel (a); nb = numel (b);
    if (size (a, 1) == 1 || size (b, 1) == 1)
      y = y.';
    endif
  elseif (ndims (a) == 2 && ndims (b) == 2 && columns (a) == columns (b))
    y = [a; b];
    na = rows (a); nb = rows (b);
  else
    error ("union: input arguments must contain the same number of columns when \"rows\" is specified");
  endif

  if (nargout == 1)
    y = unique (y, varargin{:});
  else
    [y, i] = unique (y, varargin{:});
    ia = i(i <= na);
    ib = i(i > na) - na;
  endif

endfunction

%!assert(all (all (union ([1, 2, 4], [2, 3, 5]) == [1, 2, 3, 4, 5])));

%!assert(all (all (union ([1; 2; 4], [2, 3, 5]) == [1, 2, 3, 4, 5])));

%!assert(all (all (union ([1, 2, 3], [5; 7; 9]) == [1, 2, 3, 5, 7, 9])));

%!error union (1);

%!error union (1, 2, 3);

%!test
%! a = [3, 1, 4, 1, 5]; b = [1, 2, 3, 4];
%! [y, ia, ib] = union (a, b.');
%! assert(y, [1, 2, 3, 4, 5]);
%! assert(y, sort([a(ia), b(ib)]));

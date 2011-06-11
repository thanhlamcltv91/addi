## -*- texinfo -*-
## @deftypefn {Function File} {} complement (@var{x}, @var{y})
## Return the elements of set @var{y} that are not in set @var{x}.  For
## example,
##
## @example
## @group
## complement ([ 1, 2, 3 ], [ 2, 3, 5 ])
##      @result{} 5
## @end group
## @end example
## @seealso{union, intersect, unique}
## @end deftypefn

## Author: jwe

function y = complement (a, b)

  if (nargin != 2)
    print_usage ();
  endif

  if (isempty (a))
    y = unique (b);
  elseif (isempty (b))
    y = [];
  else
    a = unique (a);
    b = unique (b);
    yindex = 1;
    y = zeros (1, length (b));
    for index = 1:length (b)
      if (all (a != b (index)))
        y(yindex++) = b(index);
      endif
    endfor
    y = y(1:(yindex-1));
  endif

endfunction

%!assert(all (all (complement ([1, 2, 3], [3; 4; 5; 6]) == [4, 5, 6])));

%!assert(all (all (complement ([1, 2, 3], [3, 4, 5, 6]) == [4, 5, 6])));

%!assert(isempty (complement ([1, 2, 3], [3, 2, 1])));

%!error complement (1);

%!error complement (1, 2, 3);


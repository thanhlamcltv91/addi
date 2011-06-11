## -*- texinfo -*-
## @deftypefn {Function File} {@var{yi} =} griddatan (@var{x}, @var{y}, @var{xi}, @var{method}, @var{options})
## 
## Generate a regular mesh from irregular data using interpolation.
## The function is defined by @code{@var{y} = f (@var{x})}.
## The interpolation points are all @var{xi}.  
##
## The interpolation method can be @code{"nearest"} or @code{"linear"}.
## If method is omitted it defaults to @code{"linear"}.
## @seealso{griddata, delaunayn}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function yi = griddatan (x, y, xi, method, varargin)

  if (nargin == 3)
    method = "linear";
  endif
  if (nargin < 3) 
    print_usage ();
  endif

  if (ischar (method))
    method = tolower (method);
  endif

  [m, n] = size (x);
  [mi, ni] = size (xi);
  
  if (n != ni || size (y, 1) != m || size (y, 2) != 1)
    error ("griddatan: dimensional mismatch");
  endif

  ## triangulate data
  ## tri = delaunayn(x, varargin{:});
  tri = delaunayn (x);

  yi = nan (mi, 1);
  
  if (strcmp (method, "nearest"))
    ## search index of nearest point
    idx = dsearchn (x, tri, xi);
    valid = !isnan (idx);
    yi(valid) = y(idx(valid));

  elseif (strcmp (method, "linear"))
    ## search for every point the enclosing triangle
    [tri_list, bary_list] = tsearchn (x, tri, xi);

    ## only keep the points within triangles.
    valid = !isnan (tri_list);
    tri_list = tri_list(!isnan (tri_list));
    bary_list = bary_list(!isnan (tri_list), :);
    nr_t = rows (tri_list);

    ## assign x,y for each point of simplex
    xt =  reshape (x(tri(tri_list,:),:), [nr_t, n+1, n]);
    yt = y(tri(tri_list,:));

    ## Use barycentric coordinate of point to calculate yi
    yi(valid) = sum (y(tri(tri_list,:)) .* bary_list, 2);

  else
    error ("griddatan: unknown interpolation method");
  endif

endfunction

%!testif HAVE_QHULL
%! [xx,yy] = meshgrid(linspace(-1,1,32));
%! xi = [xx(:), yy(:)];
%! x = (2 * rand(100,2) - 1);
%! x = [x;1,1;1,-1;-1,-1;-1,1];
%! y = sin(2*(sum(x.^2,2)));
%! zz = griddatan(x,y,xi,'linear');
%! zz2 = griddata(x(:,1),x(:,2),y,xi(:,1),xi(:,2),'linear');
%! assert (zz, zz2, 1e-10)

%!testif HAVE_QHULL
%! [xx,yy] = meshgrid(linspace(-1,1,32));
%! xi = [xx(:), yy(:)];
%! x = (2 * rand(100,2) - 1);
%! x = [x;1,1;1,-1;-1,-1;-1,1];
%! y = sin(2*(sum(x.^2,2)));
%! zz = griddatan(x,y,xi,'nearest');
%! zz2 = griddata(x(:,1),x(:,2),y,xi(:,1),xi(:,2),'nearest');
%! assert (zz, zz2, 1e-10)

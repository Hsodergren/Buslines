Implementation of a http server delivering busline.

The server runs on localhost:8080 and has two endpoints.

### /moststops[?num=10]
Returns a sorted list of all lines with the most stops.
This endpoint supports an optional parameter **num** that controlls the number of items returned.

### /lines[?ids=*id1*,*id2*]
Returns all the stops on each line.
This endpoint supports an optional paramter **ids** that filters out specific lines. **ids** is a comma separated list of ints.

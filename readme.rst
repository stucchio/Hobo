========
Hobo
========

Hobo serves a simple purpose - it is an in-memory index of a set of documents.


Dependencies
============

Apache Thrift 0.8

sl4j for logging.

When compiling, make sure you edit the build.xml file to point to the right location/version of sl4j.

See http://www.slf4j.org/manual.html for more details.

Installation
============

The server installation is easy::

    $ ant compile jar
    $ cp build/hobo.jar SOMEPLACE_IN_CLASSPATH

The python client installation is similarly easy::

    $ ant python
    $ mv build/python-hobo.tgz /tmp
    $ cd /tmp
    $ tar -xvzf python-hobo.tgz
    $ mv hobo SOMEPLACE_IN_PYTHONPATH


Usage
=====

Hobo has a simple data model. It is based on the concept of an item::

    struct Item {
        string id;
	string category;
	list<string> features;
	byte red;
	byte green;
	byte blue;
	int cost;
	int quality;
    }

A category is a string - subcategories are represented by prefixes. E.g., "/products/bags/hobo" is a subcategory of "/products/bags" or even "/prod".

Quality represents how important the item is - higher is better. Higher quality items come earlier in the list of results than lower quality items.

Cost represents how pricey the item is.

The red, green and blue values represent the color of the item. These values are signed bytes, i.e. (127,127,127) is white and (-128,-128,-128) is black.

Client
------

HoboIndex supports one method: find(1:string category_name, 2:list<string> features, 3:byte red, 4:byte green, 5:byte blue, 6:double colorDist, 7:i32 cost_min, 8:i32 cost_max, 9:i32 page)::

    find("/products/bags/hobo", ["brass buckle", "leather"], 127, -128, -128, 10, 0, 25, 0) will return a list of id's for items in /products/bags/hobo (or some subcategory), which also contain the features "brass buckle" and "leather", the color will have a CIE_LAB distance of 10 to rgb(255,0,0), and the cost will be between 0 and 25.

If no color filter is required, the color distance should be set to a negative value::

    find("/products/bags/hobo", ["brass buckle", "leather"], 0, 0, 0, -1, 0, MAX_INT, 0) will return a list of id's for items in /products/bags/hobo (or some subcategory), which also contain the features "brass buckle" and "leather".

The results are paginated - the call find(..., 0) above will return the first $PAGESIZE items, find(..., 1) will return the items from PAGESIZE to 2 x PAGESIZE, etc.

Server
------

The server is a read-only indexer. It is run as follows::

    $ java org.styloot.hobo.thriftserver.Server INPUTFILE PORT PAGESIZE

The inputfile has the following format. Each row contains an item, represented as follows::

    id;category;quality;red,green,blue;feature1,feature2,...,featuren;cost

The id, category and features must not contain semicolons, commas or newlines.




========
Hobo
========

Hobo serves a simple purpose - it is an in-memory index of a set of documents.


Dependencies
============

Apache Thrift 0.5

Hobo will not work with Thrift 0.6 (yet). It probably won't work with Thrift 0.4 ever, though I haven't tried.

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
	int quality;
    }

A category is a string - subcategories are represented by prefixes. E.g., "/products/bags/hobo" is a subcategory of "/products/bags" or even "/prod".

Quality represents how important the item is - higher is better. Higher quality items come earlier in the list of results than lower quality items.

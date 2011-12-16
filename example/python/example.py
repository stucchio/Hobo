#!/bin/python

import sys

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

from hobo.Hobo import Client

transport = TSocket.TSocket('localhost', 10289)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Client(protocol)
transport.open()

print "/dresses, [], 0 -> " + str(client.find("/dresses", [], 0))
print "/dresses/sexy, [], 0 -> " + str(client.find("/dresses/sexy", [], 0))
print "/dresses, [red], 0 -> " + str(client.find("/dresses", ["red"], 0))
print "/dresses, [short], 0 -> " + str(client.find("/dresses", ["short"], 0))
print "/skirt, [], 0 -> " + str(client.find("/skirt", [], 0))


#client.sync("mary")


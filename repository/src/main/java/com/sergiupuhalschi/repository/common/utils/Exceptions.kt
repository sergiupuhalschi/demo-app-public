package com.sergiupuhalschi.repository.common.utils

class UnexpectedNetworkResponse : RuntimeException("Failed to process network response")

class ResourceNotFoundException : RuntimeException()

class ResourceParseException : RuntimeException()
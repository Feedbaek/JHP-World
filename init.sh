#!/bin/bash

docker build . -t cpp_runner_image

docker run -d --name cpp_runner_container --network none cpp_runner_image
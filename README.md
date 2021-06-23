# TQS Project

<hr>

## Project Abstract

### Title: **WeDo4U**

### Description:

This project has the objective to implement a *multi-layered, enterprise web application with a Software Quality Assurance (SQA) strategy*, applied throughout the software engineering process.

It incorporates a **digital marketplace** to *offer on-demand household services (plumber, cleaner, furniture assembly, handyman, etc.)*, with the nearest independent service professionals.

The platform manages a dynamic workforce of service professionals; accepts orders; optimizes the assignment of jobs to the professionals; and allows scheduled visits. It will have separated areas for the professionals to input the services provided and times, and for the consumers to use the platform and order or schedule a service.

It will includes **two main “sub-projects”**:
- The **deliveries platform (“engine”)**, with use cases such as riders’ registration and reputation management, dynamic matchmaking of orders and riders, performance dashboard, etc.
- A **specific application/market proposition** that leverages on the deliveries platform, e.g., food ordering, drugs (medicines) delivery, “small” marketplace promoted by a municipality to stimulate local stores, etc.


<hr>

## Project Team

Team Coordinator - [Margarida Martins](https://github.com/margaridasmartins) (NMEC: 93169)

Product Owner - [Francisca Barros](https://github.com/itskikat/) (NMEC: 93102) 

QA Engineer - [Isadora Loredo](https://github.com/flisadora) (NMEC: 91322)

DevOps Master - [Gonçalo Matos](https://github.com/gmatosferreira) (NMEC: 92972)

<hr>

## Bookmarks

Project Backlog - [ZenHub Workspace](https://app.zenhub.com/workspaces/g305-workspace-60acdf9899b217000e989335/board?repos=368318766) + [GitHub Projects](https://github.com/itskikat/tqs_project/projects/2) 

Shared Documentation - [Google Drive](https://drive.google.com/drive/folders/1n0ijPP6LSFY4bgSBD9A0NWrg2F0YHeD3?usp=sharing)

API Documentation - tbd

Static Analysis - [Frontend Distribution Platform](https://sonarcloud.io/dashboard?id=frontend-distribution-platform) + [Frontend End User](https://sonarcloud.io/dashboard?id=frontend-end-user) + [Backend Distribution Platform](https://sonarcloud.io/dashboard?id=backend-distribution-platform) + [Backend End User](https://sonarcloud.io/dashboard?id=backend-end-user) 

CI/CD Environment - [Deployment](#Deployment)



## Deployment

We have deployed our project at a Virtual Machine provided by the university ICT services.

The VM has DNS http://deti-tqs-14.ua.pt/. (*NOTE - This is only accessible when using the University's Internal Network.*)


#### Credentials [WeDo4U](#Deployment)

| *Username/Email*     | *Password* | *Role*    |
|----------------------|------------|-----------|
| plumber@plumber.com  | abc        | Business  |
| bob.hard@outlook.com | abc        | Provider  |


#### Credentials [Plumber.com](#Deployment)

| *Username/Email* | *Password* | *Role*    |
|------------------|------------|-----------|
| xpto@ua.pt       | abc        | Client    |



### Continuos Deployment

We have set up a continuos deployment pipeline with GitHub runner, based on the official [documentation](https://docs.github.com/en/actions/hosting-your-own-runners/adding-self-hosted-runners#adding-a-self-hosted-runner-to-an-organization). It runs every time a commit is made to `main` branch.

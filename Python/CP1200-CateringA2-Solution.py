""" CP1200 Assignment 1 - Part 2 2012 SP1
    Catering Calculator 2.0 - Solution 
    Lindsay Ward
    Started: 11/03/2012 (Part 1), 13/04/2012 (Part 2)
    New version - 18/02/2014
"""

"""
Pseudocode:

function main()
    packages = []
    display welcome message
    display menu
    get menu choice
    while menu choice is not q
        if menu choice is i
            display instructions    
        else if choice is c
            calculateCatering(packages)
        else if menu choice is a
            package = getPackage()
            append package to packages
        else if menu choice is d
            displayPackages(packages)        
        else if menu choice is s
            get file name
            savepackages(packages, file name)
        else if menu choice is l
            get file name
            packages = loadpackages(file name)
        else
            display error message
        display menu
        get menu choice
    print farewell message

function calculateCatering(packages)
    number of adults = getValidInt()
    number of children = getValidInt()
    displayPackages()
    package choice = getValidInt()
    while package choice > length of packages
        display error message
        package choice = getValidInt()
    displayDeliveryChoics()
    get delivery choice
    while delivery choice is not in list of keys from DELIVERY_COSTS dictionary
        display error message
        get delivery choice
    
    adult cost = packages[package choice][1]
    child cost = packages[package choice][2]
    if delivery choice is none
        delivery cost = 0
        delivery message = "(pick up)"
    else
        delivery cost = value of delivery choice from DELIVERY_COSTS dictionary
        delivery message = delivery choice
        
    package message = packages[package choice][0]
    if random number between 1 and 10) is 1
        append "free" message to package message
        cost = (number of adults + number of children) * child cost
    else
        cost = number of adults * adult cost + number of children * child cost
    cost = cost + delivery cost
    display cost, package message, number of adults, number of children, delivery message

function getPackage()
    get name
    while name is ""
        get name
    adult cost = getValidFloat()
    child cost = getValidFloat()
    return name, adult cost, child cost as tuple

function displayPackages(packages)
    if length of packages is 0
        display no packages message
        return
    for each package in packages
        display package[0], package[1], package[2]

function displayDeliveryChoices()
    for each area and cost in DELIVERY_COSTS dictionary
        display area and cost

function loadPackages(file name)
    packages = []
    open input file (file name) for reading
    for each line in input file
        name, adultCost, childCost = split line at ,
        append tuple(name, adultCost as float, childCost as float) to packages
    close input file
    return packages

function savePackages(packages, file name)
    open output file (file name) for reading
    for each package in packages
        write package[0], package[1], package[2], new line to output file
    close output file

function getValidInt(prompt, error, minimum)
    while true
        try
            get x as int
            if x >= minimum
                return x
        except
            print error

function getValidFloat(prompt, error, minimum)
    while true
        try
            get x as int
            if x >= minimum
                return x
        except
            print error
            
"""

import random

MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(D)isplay Packages\n(L)oad Packages\n(S)ave Packages\n(A)dd Package\n(Q)uit"
INSTRUCTIONS = "This program allows you to calculate catering costs based on choice of package and number of adults and children.\n\
You can load packages from a file, add new ones and save the file for next time."
NO_PACKAGES_MESSAGE = "You need to add or load a package first."
WIDTH = 16
DELIVERY_COSTS = {"North": 8.5, "South": 17.5, "West": 15, "East": 15}


def main():
    """ Catering Calculator program, with packages and delivery options - for CP1200 assignment 2 """
    packages = []
    print("Welcome to the Great CP1200 Catering Calculator 2.0!")
    print("Written by Lindsay Ward, February 2014")
    print(MENU)
    menuChoice = input(">>> ").upper()
    while menuChoice != "Q":
        if menuChoice == "I":
            print(INSTRUCTIONS)
        elif menuChoice == "C":
            if len(packages) == 0:
                print(NO_PACKAGES_MESSAGE)
            else:
                calculateCatering(packages)
        elif menuChoice == "D":
            if len(packages) == 0:
                print(NO_PACKAGES_MESSAGE)
            else:
                displayPackages(packages)
        elif menuChoice == "L":
            packages = loadPackages()
            print(len(packages), "packages loaded")
        elif menuChoice == "S":
            if len(packages) == 0:
                print(NO_PACKAGES_MESSAGE)
            else:
                savePackages(packages)
                print(len(packages), "packages saved")
        elif menuChoice == "A":
            package = getPackage()
            packages.append(package)
            print(package[0], "added")
        else:
            print("Invalid menu choice.")
        print(MENU)
        menuChoice = input(">>> ").upper()
    print("Thank you for using the Great CP1200 Catering Calculator 2.0.")


def calculateCatering(packages):
    """ get client details for catering job and calculate total cost """
    numberAdults = getValidInt("Please enter the number of adults: ", "Number must be valid and >= 0")
    numberChildren = getValidInt("Please enter the number of children: ", "Number must be valid and >= 0")
    displayPackages(packages)
    packageChoice = getValidInt("Which package would you like?: ", "Number must be valid and >= 1", 1)
    while packageChoice > len(packages):
        print("Number must be <=", len(packages))
        packageChoice = getValidInt("Which package would you like?: ", "Number must be valid and >= 1", 1)
    packageChoice = int(packageChoice) - 1

    displayDeliveryChoices()
    deliveryChoice = input("Which delivery area would you like?: ").title()
    while deliveryChoice not in list(["None", ""] + list(DELIVERY_COSTS.keys())):
        print("Invalid area - type the word.")
        deliveryChoice = input("Which delivery area would you like?: ").title()

    # calculate and print catering details
    packageMessage = packages[packageChoice][0]
    childCost = packages[packageChoice][2]
    if deliveryChoice == "None" or deliveryChoice == "":
        deliveryCost = 0
        deliveryMessage = " (pick up)"
    else:
        deliveryCost = DELIVERY_COSTS[deliveryChoice]
        deliveryMessage = ", delivered to " + deliveryChoice

    # 1 in 10 random chance of getting the premium service for free
    if random.randint(1, 10) == 1:
        cost = (numberAdults + numberChildren) * childCost
        packageMessage += " (adults at kids' prices!)"
    else:
        adultCost = packages[packageChoice][1]
        cost = numberAdults * adultCost + numberChildren * childCost
    cost += deliveryCost

    if numberAdults == 1:
        adultsMessage = "adult"
    else:
        adultsMessage = "adults"
    if numberChildren == 1:
        childrenMessage = "child"
    else:
        childrenMessage = "children"
    # print("\nThat will be $%0.2f for the %s package for %d %s and %d %s%s. Enjoy!" % (cost, packageMessage, numberAdults, adultsMessage, numberChildren, childrenMessage, deliveryMessage))
    print("\nThat will be ${:0.2f} for the {} package for {} {} and {} {}{}. Enjoy!".format(cost, packageMessage,
                                                                                            numberAdults, adultsMessage,
                                                                                            numberChildren,
                                                                                            childrenMessage,
                                                                                            deliveryMessage))


def displayPackages(packages):
    """ display all of the catering packages """
    for i in range(len(packages)):
        # print("%d. %*s - $%5.2f / $%5.2f" % (i + 1, WIDTH, packages[i][0], packages[i][1], packages[i][2]))
        print("{}. {:{width}} - ${:5.2f} / ${:5.2f}".format(i + 1, packages[i][0], packages[i][1], packages[i][2],
                                                            width=WIDTH))


def displayDeliveryChoices():
    """ display the delivery choices (dictionary """
    print("None")
    for area, cost in DELIVERY_COSTS.items():
        # print("%5s - $%5.2f" % (area, cost))
        print("{:<5} - ${:5.2f}".format(area, cost))


def getPackage():
    """ get valid package details, return tuple of name, price, qty """
    name = input("Enter package name: ")
    while name == "" or len(name) > WIDTH:
        print("Package name can not be blank and must be less than", WIDTH + 1, "characters")
        name = input("Enter package name: ")
    adultPrice = getValidFloat("Enter package price per adult: $", "Price must be valid and >= $0.01.", 0.01)
    childPrice = getValidFloat("Enter package price per child: $", "Price must be valid and >= $0.01.", 0.01)
    return name, adultPrice, childPrice


def loadPackages(fileName="packages.txt"):
    """ load packages from file stored as name,adultPrice,childPrice """
    packages = []
    inFile = open(fileName)
    for line in inFile:
        name, adultPrice, childPrice = line.split(',')
        packages.append((name, float(adultPrice.strip()), float(childPrice.strip())))
    inFile.close()
    return packages


def savePackages(packages, fileName="packages.txt"):
    """ save package list to file as name,adultPrice,childPrice per line """
    outFile = open(fileName, 'w')
    for package in packages:
        outFile.write(package[0] + "," + str(package[1]) + "," + str(package[2]) + "\n")
    outFile.close()


def getValidInt(prompt, error, minimum=0):
    """ get a valid int - loop until valid """
    while True:
        try:
            x = int(input(prompt))
            if x >= minimum:
                return x
            raise ValueError
        except:
            print(error)


def getValidFloat(prompt, error, minimum=0):
    """ get a valid float - loop until valid """
    while True:
        try:
            x = float(input(prompt))
            if x >= minimum:
                return x
            raise ValueError
        except:
            print(error)

# def getValidNumber(prompt, error, minimum=0):
# while True:
#         x = input(prompt)
#         if isFloat(x) and float(x) >= minimum:
#             return float(x)
#         else:
#             print(error)
#             
# def isFloat(x):
#     try:
#         x = float(x)
#     except:
#         return False
#     return True

main()
# displayDeliveryChoices()
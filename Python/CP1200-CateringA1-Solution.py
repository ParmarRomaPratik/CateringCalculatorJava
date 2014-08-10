""" CP1200 Assignment 1 - 2014
    Catering Calculator - Solution 
    Lindsay Ward
    18/02/2014

Pseudocode:

COST_PER_HEAD = 10.0
CHILD_RATE = 0.6
PREMIUM_RATE = 1.25

function main()
    display welcome message
    display menu
    get choice
    while choice is not 'q'
        if choice is 'i'
            display instructions    
        else if choice is 'c'
            call calculateAndPrintCatering()
        else
            display invalid choice message
        display menu
        get choice
    print farewell message


function calculateAndPrintCatering()        
    get number of adults
    while number of adults is < 0
        display error message
        get number of adults
    get number of children
    while number of children < 0
        display error message
        get number of children
    get service type
    while service type is not 'b' and service type is not 'p'
        display error message
        get service type 

    cost = number of adults * COST_PER_HEAD + number of children * COST_PER_HEAD * CHILD_RATE
    service message = 'basic'
    if service type is 'p'
        cost = cost * PREMIUM_RATE
        service message = 'premium'
    if number of adults is 1
        adult message = 'adult'
    else
        adult message = 'adults'
    if number of children is 1
        child message = 'child'
    else
        child message = 'children'
    display cost, service type, number of adults, adult message, number of children, child message, service message 

"""


COST_PER_HEAD = 10.0
CHILD_RATE = 0.6  # 60%
PREMIUM_RATE = 1.25
MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(Q)uit"
INSTRUCTIONS = "Enter number of adults and children and choose a service type.\n\
Basic:   food only    = $%0.2f per adult\n\
Premium: food & drink = $%0.2f per adult\n\
Children are always %0d%% of the price of adults." % (COST_PER_HEAD, COST_PER_HEAD * PREMIUM_RATE, CHILD_RATE * 100)


def main():
    print("Welcome to the Great CP1200 Catering Calculator!")
    print("Written by Lindsay Ward, February 2014")
    
    print(MENU)
    choice = input(">>> ").upper()
    while choice != "Q":
        if choice == "I":
            print(INSTRUCTIONS)
        elif choice == "C":
            calculateAndPrintCatering()        
        else:
            print("Invalid menu choice.")
        print(MENU)
        choice = input(">>> ").upper()
        
    print("Thank you for using the Great CP1200 Catering Calculator.")


def calculateAndPrintCatering():
    """ get details for catering job; calculate costs based on inputs and constant rates; print results """
    numberAdults = int(input("Please enter the number of adults: "))
    while numberAdults < 0:
        print("Error. Please enter a number >= 0")
        numberAdults = int(input("Please enter the number of adults: "))
    
    numberChildren = int(input("Please enter the number of children: "))
    while numberChildren < 0:
        print("Error. Please enter a number >= 0")
        numberChildren = int(input("Please enter the number of children: "))
    
    serviceType = input("Would you like (B)asic or (P)remium service?: ").upper()
    while serviceType not in ('B', 'P'):
        print("Error. Please enter b or p")
        serviceType = input("Would you like (B)asic or (P)remium service?: ").upper()

    # calculate catering details
    cost = numberAdults * COST_PER_HEAD + numberChildren * COST_PER_HEAD * CHILD_RATE
    serviceMessage = "basic"
    if serviceType == 'P':
        # multiply basic total cost by premium rate to get premium total 
        cost *= PREMIUM_RATE
        serviceMessage = "premium"

    # print results, after determining correct singular/plural words    
    if numberAdults == 1:
        adultWord = "adult"
    else:
        adultWord = "adults"
    if numberChildren == 1:
        childWord = "child"
    else:
        childWord = "children"
    print("\nThat will be $%0.2f for the %s service for %d %s and %d %s. Enjoy!" % (cost, serviceMessage, numberAdults, adultWord, numberChildren, childWord))

main()
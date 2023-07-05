REST API hooks:

/api/v1/

    auth/

        /register

            @Post
            /buyer

            @Post
            /seller

        @Post
        /authenticate

        /Buyer

            @Get

    @Post
    energyPacket/

    energy/

        @Get
        procuced/

        @Get
        consumed/


    user/

        @get
        {email}/ (gets all data of a seller or a buyer that matches that email)

        sellerOrBuyer/

            @get
            {email} (gets if a user is a seller or a buyer)

    buyer/

        @Get 
        {id}/  (gets all data of a buyer)
    
            @Get
            battery/

            @Get
            batteryPercentage/

    seller/

        @Get 
        {id}/  (gets all data of a seller)

            @Put
            incrementPanels/

            @Put
            startSelling/

            @Put
            stopSelling/

            @Get
            battery/

            @Get
            batteryPercentage/

    @get
    time/

    @get
    weather/

    transactions/

        @get
        getAll/

        @get
        getBySeller/

        @get
        getByBuyer/

        @get
        getSellerRevenue/

        @get
        getBuyerTotalExpenses/

        @get
        getBuyerTotalEnergyBuyed/

        @get
        getSellerTotalEnergySold/

        @post
        add/
    


    
    


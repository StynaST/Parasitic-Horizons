// Remove the default recipes.
def recipes = [
    'environmentaltech:solar_cont_6',
    'environmentaltech:solar_cont_5',
    'environmentaltech:solar_cont_4',
    'environmentaltech:void_ore_miner_cont_6',
    'environmentaltech:void_ore_miner_cont_5',
    'environmentaltech:void_ore_miner_cont_4',
    'environmentaltech:void_res_miner_cont_5',
    'environmentaltech:void_res_miner_cont_4',
    'environmentaltech:void_botanic_miner_cont_6',
    'environmentaltech:void_botanic_miner_cont_5',
    'environmentaltech:void_botanic_miner_cont_4'
]

recipes.each { crafting.removeByOutput(item(it)) }
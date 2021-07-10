from recommendation_system import RecommendationSystem

if(__name__=="__main__"):
	# Create a recommendation system with files paths
	# Rate 20 movies
	# Neighborhood of 10 neighbours
	system = RecommendationSystem(f"../ml-data/u.data", f"../ml-data/u.item", 20, 10, "../ml-data/u.recommendations")

	# Rate 20 movies
	system.set_initial_ratings()
	
	# Calculate the neighborhood with initial ratings
	system.calculate_neighborhood()

	# Get the neighborhood
	neighborhood = system.get_neighborhood()

	# Print neighborhood
	print(f"The actual neighborhood for user is (user:similarity):\n {neighborhood}")

	# Calculate the recommendations by neighborhood
	system.calculate_recommendations()

	# Get the recommendations
	recommendations = system.get_recommendations()

	# Print the recommendations
	print(f"The recommendations for user is (idMovie:rating):\n {recommendations}\n")

	# Ask the user to print or save the recommendations
	system.see_recommendations()
from pandas import DataFrame, read_csv
from numpy import corrcoef, argsort, mean, array

class RecommendationSystem():
	# Constructor
	def __init__(self, ratings_file: str, movies_file: str, num_to_rate: int, neighborhood: int, export: str):
		# Read ratings from ratings_file path
		ratings = ["idUser","idMovie","rating","timestamp"]
		self.__ratings = read_csv(ratings_file, 
			engine='python',
			sep='\t', 
			header=None,
			names=ratings)
		# Read movies from movies_file path
		movies = ["idMovie","title","date"]
		self.__movies = read_csv(movies_file, 
			engine='python',
			sep='|', 
			usecols=range(0,3),
			header=None,
			names=movies)
		# Number of movies to be rated by the user
		self.__num_to_rate = num_to_rate
		# Number of the neighborhood to be generated
		self.__num_neighborhood = neighborhood
		# User neighborhood
		self.__neighborhood = {}
		# User ratings
		self.__user_ratings = {}
		# User recommendations
		self.__recommendations = {}
		# File to export
		self.__export = export


	# Set initial ratings by input console
	def set_initial_ratings(self):
		# Get num_to_rate random movies to rate by user
		rated_movies = self.__movies.sample(self.__num_to_rate)

		print(f"\n\nUSER RATINGS: Please rate each movie from 1 to 5 stars.\n\n")

		# Define a count
		count = 1
		# Let user rate each movie
		for movie in rated_movies["idMovie"]:
			# Get movie title
			movie_title = rated_movies[rated_movies["idMovie"]==movie]["title"].values[0]

			while True:
				print(f"{count}. {movie_title}")
				# Get user input as rating
				rating = int(input())
				# Check rating is valid
				if rating not in range(1,6):
					print("Input rating is not valid, ratings must be between 1 and 5 stars.")
				else:
					break

			# Save rating associated to movie
			self.__user_ratings[movie] = rating
			count += 1

	# Calculate the neighborhood associated to user ratings
	def calculate_neighborhood(self):
		# Group users by ID into a set
		users = set(self.__ratings["idUser"])
		# Add rating user as 0
		users.add(0)

		# Create a matrix that groups the users and movies
		ratings_matrix = DataFrame(index=users, columns=self.__movies["idMovie"])
		# Fill all the matrix with 0
		ratings_matrix = ratings_matrix.fillna(0)
		# Add user ratings to matrix
		for movie in self.__user_ratings.keys():
			ratings_matrix.iloc[0, movie-1] = self.__user_ratings[movie]
		# Add rest of users ratings to matrix
		for user in users:
			# Get user ratings
			user_ratings = self.__ratings[self.__ratings["idUser"] == user]
			# Add user ratings to matrix
			for movie in user_ratings["idMovie"]:
				ratings_matrix.iloc[user, movie-1] = user_ratings[user_ratings["idMovie"]==movie]["rating"].values[0]

		# Calculate correlation coefficients matrix from ratings matrix 
		corr_matrix = corrcoef(ratings_matrix)
		# The first column contains the comparison between actual user and rest of users
		corr = corr_matrix[0]
		# Sort indexes (users id) in descending order (from best to worst)
		users_order = argsort(corr)[::-1]

		# Create the neighborhood by selecting the n-best users compared to actual user
		for i in range(self.__num_neighborhood):
			self.__neighborhood[users_order[i+1]]=corr[users_order[i+1]]

	# Get the neighborhood
	def get_neighborhood(self):
		return self.__neighborhood

	# Calculate recommendations based on users ratings
	def calculate_recommendations(self):
		# Get user ratings
		rated_movies = self.__user_ratings.keys()
		# Calculate mean of user ratings
		user_mean = mean(list(self.__user_ratings.values()))
		# Calculate mean of user neighborhood ratings
		neighborhood_mean = {}
		for neighbour in self.__neighborhood.keys():
			neighbour_rating = self.__ratings[self.__ratings["idUser"] == neighbour]
			neighbour_mean = neighbour_rating["rating"].mean()
			neighborhood_mean[neighbour] = neighbour_mean

		# Search every possible movie
		for movie in array(self.__movies["idMovie"]):
			# Search movies not rated by user
			if movie not in rated_movies:
				sim, corrC = 0, 0
				# Get neighborhood rating
				for neighbour in self.__neighborhood.keys():
					# Get neighbour correlation
					corr = self.__neighborhood[neighbour]
					# Get movie rating by neighbour
					movie_rating = self.__ratings[(self.__ratings["idMovie"]==movie) & (self.__ratings["idUser"]==neighbour)]
					# Check that neighbour has rated the movie
					if not movie_rating.empty:
						# Get finally the movie rating by user
						movie_rating = movie_rating["rating"].values[0]
						# Sum into sim and corrC
						sim += corr*(movie_rating - neighborhood_mean[neighbour])
						corrC += abs(corr)

				# Calculate interest for user
				interest = 0
				if corrC > 0:
					# Calculate the interest value
					interest = user_mean + sim/corrC

					# Force higher interest to be 5 stars
					if interest > 5:
						interest = 5
				# Check if interest is higher than 4 starts (then it is a recommendation)
				if interest >= 4:
					self.__recommendations[movie]=interest

		# Sort recommendations in descending order from best to worst
		self.__recommendations = dict(sorted(self.__recommendations.items(), key=lambda item: item[1], reverse=True))

	# Get the recommendations
	def get_recommendations(self):
		return self.__recommendations

	# Print recommendations
	def print_recommendations(self):
		count = 1
		for movie in self.__recommendations.keys():
			title = self.__movies[self.__movies["idMovie"] == movie]["title"].values[0]
			print(f"{count}. {title}: {str(self.__recommendations[movie])}")
			count += 1

	# Export recommendations
	def export_recommendations(self):
		f = open(self.__export, "w")
		count = 1
		for movie in self.__recommendations.keys():
			title = self.__movies[self.__movies["idMovie"] == movie]["title"].values[0]
			f.write(f"{count}. {title}: {str(self.__recommendations[movie])}\n")
			count += 1
		f.close()

	# See recommendations
	def see_recommendations(self):
		# See recommendations
		while True:
			# Ask user to print o export recommendations
			print(f"\nDo you want to see your recommendations? (y/n)")
			# Get user input
			user_input = input()
			if user_input == 'y':
				self.print_recommendations()
				break
			elif user_input == 'n':
				break

		# Export recommendations
		while True:
			# Ask user to print o export recommendations
			print(f"\nDo you want to export your recommendations? (y/n)")
			# Get user input
			user_input = input()
			if user_input == 'y':
				self.export_recommendations()
				print(f"\n\nRecommendations exported successfully on ..ml-data/u.recommendations")
				break
			elif user_input == 'n':
				break
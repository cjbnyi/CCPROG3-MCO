PROJECT_NAME = hotel
SRC_DIR := src/Hotel
BIN_DIR := bin
TST_DIR := tests
DOC_DIR := docs/jdoc



SRCS = $(wildcard $(SRC_DIR)/*.java)
TESTS := $(wildcard $(TST_DIR)/*.java))

JC := javac
JDOC := javadoc
JRUN := java


JCFLAGS = -d $(BIN_DIR)/ -cp $(SRC_DIR)/
JDFLAGS = -d $(DOC_DIR)/ -cp $(SRCS)
JRFLAGS = -cp $(BIN_DIR)/

all: build doc run

# Does not produce execution output.
.SILENT:
build: $(SRCS)
	@echo "=== Building . . . ===================================="
	@echo ""
	for file in $^; \
	do \
		echo "   Building... $${file}";\
	done
	@echo ""
	$(JC) $(JCFLAGS) $^
	@echo ""
	@echo "=== Building Complete ================================="

doc: $(DOC_DIR)
	@echo "=== Documenting Files ... ============================="
	@echo ""
	$(JDOC) $(JDFLAGS)

run: $(BIN_DIR)
	@echo "=== Running Hotel Program...========================== "
	@echo ""
	$(JRUN) $(JRFLAGS) Hotel.Main

test:
	@echo "\nBuilding\n"
	$(CC) $(TST_DIR)/test.exe $^
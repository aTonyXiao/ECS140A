;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;Tony Xiao Feb.23rd;;;;;;;;;;;;
;;;;;;;;;;;;;;;Part 1;;;;;;;;;;;;;;;;;;
;all-length, that takes a list and counts the number of atoms that occur in a list at all levels.
; (readable:enable-basic-curly)\
; (set-syntax-from-char #\{ #\( )
(defun all-length(lista)
  (cond
    ((null lista) 0)
    ((atom lista) 1)
    ((+ (all-length (car lista)) (all-length (cdr lista))))))
;http://stackoverflow.com/questions/26289607/is-there-anything-similar-to-return-statement-of-c-in-lisp
;   (if (null lista) 
;     0
;     (if (atom lista) 
;     1
;     )
;     (setf len (+ (all-length (car lista)) (all-length (cdr lista))))
;   len
;   )
; )
  
  


;range that takes a list of numbers (with at least one element) and returns a list of length 2 of the smallest and largest numbers.
(defun range(lista)
  (list (apply 'min lista) (apply 'max lista))) ;http://www.programcreek.com/2010/10/lisp-code-find-minimum-element-in-a-list/

;; edit it 
;before that searches a list and returns a list of all elements in between the first two arguments (inclusive).
(defun before(begin end lista)
  (cond
    ((null lista) nil);empty list
    ((equal begin (car lista)) (cons begin (getend end (cdr lista))))
    ((before begin end (cdr lista)))))

(defun getend(end lista)
  (cond
    ((null lista) nil)
    ((equal end (car lista)) (list end))
    ((cons (car lista) (getend end (cdr lista))))))

;split-if that returns a list into two parts. It takes two arguments: a function (f) and a list. All members for which f is true go into one list, and the rest go into another list.
(defun split-if (func lista)
  (list (splitrue func lista) (splitfalse func lista)))

(defun splitrue(func lista)
  (cond
    ((null lista) nil)
    ((funcall func (car lista)) (cons (car lista) (splitrue func (cdr lista))))
    ((splitrue func (cdr lista)))))

(defun splitfalse(func lista)
  (cond
    ((null lista) nil)
    ((not (funcall func (car lista))) (cons (car lista) (splitfalse func (cdr lista))))
    ((splitfalse func (cdr lista)))))


;;edit here
;group that takes arguments: a list l and a number n. It returns a new list in which the elements of l are grouped into sublists of length n. The remainder is put in a final sublist.
(defun group(l n)
  (setf count(floor(/ (list-length l) n)))
  (setf begin 0)
  (setf end 0)
  (setf res '())
  (groupHelp count begin end res l n)
  )

(defun groupHelp(count begin end res l n)
  (loop for i from 1 to count
    do (setf end (+ begin n))
    do (setf res (append res (list(subseq l begin end))))
    do (setf begin (+ begin n)))
  (if (not (null (subseq l end (length l)))) ; fix here
    (setf res (append res (list (subseq l end (length l))))))
res
)


(defun mostn(f l)
  (setf res '())
  (setf num (reduce (function max)(mapcar f l)))
  (mostnHelp f l res num))

(defun mostnHelp(f l res num)
  (loop for i in l
    do (if (= num (funcall f i))
      (setf res (append res (list i)))))
  (setf res (append (list res) (list num)))
  res
  )
;Part2
(defun match(assertion1 assertion2)
  (cond
    ((and (null assertion1) (not (null assertion2))) nil); 1 nill 2 not
    ((and (null assertion2) (not (null assertion1))) nil); 2 nill 1 not
    ((and (null assertion1) (null assertion2)) T) ; 1,2 nil
    ((and (not (null assertion1)) (not (null assertion2))(equal assertion1 assertion2))) ;1 2 not nill
    ((equal (car assertion1) '?) (match (cdr assertion1) (cdr assertion2))); move to next one
    ((equal (car assertion1) '!) (matchHelp assertion1 assertion2))
    (T (matchDefault assertion1 assertion2))))

(defun matchHelp (assertion1 assertion2); work for !
  (if (match (cdr assertion1) (cdr assertion2))
    T
    (match assertion1 (cdr assertion2))
    )
)

(defun matchDefault(assertion1 assertion2)
  (setf facialEq (equal (car assertion1) (car assertion2)))
  (setf deepEq (match (cdr assertion1) (cdr assertion2)))
  (and facialEq deepEq)
)

